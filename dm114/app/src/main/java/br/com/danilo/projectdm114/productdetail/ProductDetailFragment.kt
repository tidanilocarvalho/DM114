package br.com.danilo.projectdm114.productdetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import br.com.danilo.projectdm114.R
import br.com.danilo.projectdm114.databinding.FragmentProductDetailBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

private const val TAG = "ProductDetailFragment"

class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding

    private val productDetailViewModel: ProductDetailViewModel by lazy {
        ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater);

        binding.setLifecycleOwner(this)

        binding.productDetailViewModel = productDetailViewModel

        if (this.arguments != null) {
            if (this.arguments!!.containsKey("orderId")) {
                val orderId = this.arguments!!.get("orderId").toString()

                productDetailViewModel.productDetail.value = ProductDetail(orderId = orderId, orderDate = "a", status = "a")
            }
        }

        val remoteConfig = Firebase.remoteConfig
        setHasOptionsMenu(remoteConfig.getBoolean("delete_detail_view"))

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.signout_menu, menu)
        inflater.inflate(R.menu.product_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_order -> {
                binding.productDetailViewModel?.deleteOrder()
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}