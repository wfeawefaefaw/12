package com.example.praktik_zadanie3__penkov.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.praktik_zadanie3__penkov.R
import com.example.praktik_zadanie3__penkov.activity.NewPostActivity.Companion.textArg
import com.example.praktik_zadanie3__penkov.adapter.OnInteractionListener
import com.example.praktik_zadanie3__penkov.adapter.PostsAdapter
import com.example.praktik_zadanie3__penkov.databinding.FragmentFeedBinding
import com.example.praktik_zadanie3__penkov.repository.Post
import com.example.praktik_zadanie3__penkov.viewmodel.PostViewModel


class FeedFragment : Fragment() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
private val viewModel: PostViewModel by viewModels(
    ownerProducer = ::requireParentFragment
)
override fun onCreateView(
    inflater:LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
):View? {
    val binding: FragmentFeedBinding = FragmentFeedBinding.inflate(
        inflater,
        container,
        false
    )
//    val newPostActivity = registerForActivityResult(NewPostResultContract()) { result ->
//        result ?: return@registerForActivityResult
//        viewModel.changeContent(result)
//        viewModel.save()
//    }
    val adapter = PostsAdapter(object : OnInteractionListener {
        override fun onVideo(post: Post) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=5mGdCO7kNF0"))
            startActivity(intent)
        }

        override fun onEdit(post: Post) {
            viewModel.edit(post)
//                val text = post.content
//                newPostActivity.launch(text)
            val bundle = Bundle()
            bundle.textArg = post.content
            findNavController().navigate(R.id.action_feedFragment_to_newPostActivity, bundle)
        }
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }
        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
        }
        override fun onShare(post: Post) {
            viewModel.shareById(post.id)
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)

        }
        override fun onAuthorClicked(post: Post) {
            val bundle = Bundle()
            bundle.putInt("postId", post.id)
            findNavController().navigate(R.id.action_feedFragment_to_oneFragmentPost3, bundle)

        }

    })
    binding.list.adapter=adapter
    viewModel.data.observe(viewLifecycleOwner) { posts ->
        adapter.submitList(posts)
    }

//    viewModel.edited.observe(viewLifecycleOwner){ post->
//        if(post.id == 0){
//            return@observe
//        }
//
//    }
    binding.fab.setOnClickListener {
//            val text = ""
//            newPostLauncher.launch(text)

            findNavController().navigate(R.id.action_feedFragment_to_newPostActivity)
        }
    return binding.root

}


//        binding.cancel.setOnClickListener {
//            newPostLauncher.launch(null)
//
//        }
//        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
//            result ?: return@registerForActivityResult
//            viewModel.changeContent(result)
//            viewModel.save()
//        }
//        binding.fab.setOnClickListener {
//            val text = ""
//            newPostLauncher.launch(text)
//
//        }
//        binding.fab.setOnClickListener{
//            newPostLauncher.launch(null)
//        }
//run{
//    val preferences = getPreferences(Context.MODE_PRIVATE)
//    preferences.edit().apply(){
//        putString("key","value")
//        commit()
//    }
//}
//        run {
//            getPreferences(Context.MODE_PRIVATE)
//                .getString("key", "no value")?.let {
//                    Snackbar.make(binding.root, it, BaseTransientBottomBar.LENGTH_INDEFINITE)
//                        .show()
//                }
//        }
    }







