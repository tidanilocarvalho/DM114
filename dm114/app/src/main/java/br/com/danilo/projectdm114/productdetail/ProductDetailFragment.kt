package br.com.danilo.projectdm114.productdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.com.danilo.projectdm114.databinding.FragmentOrderDetailInfoBinding
import br.com.danilo.projectdm114.databinding.FragmentProductDetailBinding
import com.google.firebase.iid.FirebaseInstanceId
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

private const val TAG = "ProductDetailFragment"

class ProductDetailFragment : Fragment() {
    private val productDetailViewModel: ProductDetailViewModel by lazy {
        ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductDetailBinding.inflate(inflater);

        binding.setLifecycleOwner(this)

        binding.productDetailViewModel = productDetailViewModel

        if (this.arguments != null) {
            if (this.arguments!!.containsKey("orderId")) {
                productDetailViewModel.productDetail.value = ProductDetail(orderId = "a", orderDate = "a", status = "a")
            }
        }
        return binding.root
    }
}