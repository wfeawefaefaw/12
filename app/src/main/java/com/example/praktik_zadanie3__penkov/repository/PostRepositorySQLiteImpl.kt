package com.example.praktik_zadanie3__penkov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.btpit.zadanie2.PostDao.PostDao

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }

    override fun likeById(id: Int)
        {
            dao.like(id)
            posts = posts.map {
                if (it.id != id) it else it.copy(
                    likedByMe = !it.likedByMe,
                    likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
                )
            }
            data.value = posts
        }

    override fun shareById(id: Int) {
        dao.like(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                share =  it.share + 1
            )
        }
        data.value = posts
    }

    override fun postID(id: Int): LiveData<Post> {
        val postLiveData = MutableLiveData<Post>()
        postLiveData.value = posts.find { it.id == id }

        return postLiveData
    }

    override fun removeById(id: Int) {
        dao.removeById(id)
        posts = posts.filter { it.id != id }
        data.value = posts
    }
}

