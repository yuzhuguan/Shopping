package com.vishalgaur.shoppingapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vishalgaur.shoppingapp.R
import com.vishalgaur.shoppingapp.data.Product
import com.vishalgaur.shoppingapp.databinding.FragmentFavoritesBinding
import com.vishalgaur.shoppingapp.ui.RecyclerViewPaddingItemDecoration
import com.vishalgaur.shoppingapp.viewModels.HomeViewModel

private const val TAG = "FavoritesFragment"

class FavoritesFragment : Fragment() {
	private lateinit var binding: FragmentFavoritesBinding
	private val viewModel: HomeViewModel by activityViewModels()
	private lateinit var productsAdapter: LikedProductAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentFavoritesBinding.inflate(layoutInflater)

		setViews()
		setObservers()
		return binding.root
	}

	private fun setViews() {
		binding.favTopAppBar.topAppBar.title = "Favorite Products"
		binding.favTopAppBar.topAppBar.setNavigationOnClickListener {
			findNavController().navigateUp()
		}
		val proList = viewModel.getLikedProducts()
		if (context != null) {
			productsAdapter = LikedProductAdapter(proList, requireContext())
			productsAdapter.onClickListener = object : LikedProductAdapter.OnClickListener {
				override fun onClick(productData: Product) {
					Log.d(TAG, "Product: ${productData.productId} clicked")
					findNavController().navigate(
						R.id.action_favoritesFragment_to_productDetailsFragment,
						bundleOf("productId" to productData.productId)
					)
				}

				override fun onDeleteClick(productId: String) {
					TODO("Not yet implemented")
				}
			}
			binding.favProductsRecyclerView.apply {
				adapter = productsAdapter
				val itemDecoration = RecyclerViewPaddingItemDecoration(requireContext())
				if (itemDecorationCount == 0) {
					addItemDecoration(itemDecoration)
				}
			}
		}
	}

	private fun setObservers() {
		viewModel.userLikes.observe(viewLifecycleOwner) {
			if(it.isNotEmpty()) {
				binding.loaderLayout.circularLoader.visibility = View.GONE
				binding.loaderLayout.circularLoader.hideAnimationBehavior
				binding.favProductsRecyclerView.adapter?.apply {
					productsAdapter.data = viewModel.getLikedProducts()
					notifyDataSetChanged()
				}
			}
		}
	}
}