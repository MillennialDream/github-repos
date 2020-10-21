package com.sbnri.assignment.data

import androidx.paging.*
import androidx.room.withTransaction
import com.sbnri.assignment.data.local.GithubDb
import com.sbnri.assignment.data.local.RepoDao
import com.sbnri.assignment.data.remote.GithubService
import com.sbnri.assignment.data.local.RemoteKeyDao
import com.sbnri.assignment.model.RemoteKey
import com.sbnri.assignment.model.Repo
import retrofit2.HttpException
import java.io.IOException

private const val PAGING_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class GithubPagingSource(
    private val db: GithubDb,
    private val service: GithubService
) : RemoteMediator<Int, Repo>() {
    private val keyDao: RemoteKeyDao = db.remoteKeyDao()
    private val repoDao: RepoDao = db.repoDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Repo>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {

                    val remoteKey = db.withTransaction {
                        keyDao.remoteKeyByType(REMOTE_KEY_TYPE_REPO)
                    }

                    if (remoteKey.nextPageKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextPageKey
                }
            }

            val position = loadKey ?: PAGING_STARTING_PAGE_INDEX

            val repos = service.repos(position, state.config.pageSize)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    repoDao.deleteAll()
                    keyDao.deleteByType(REMOTE_KEY_TYPE_REPO)
                }

                keyDao.insert(RemoteKey(REMOTE_KEY_TYPE_REPO, position + 1))
                repoDao.insertAll(repos)
            }

            MediatorResult.Success(endOfPaginationReached = repos.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val REMOTE_KEY_TYPE_REPO = "repo"
    }
}
