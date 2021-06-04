package com.example.buynow.Fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.buynow.Adapter.CoverProductAdapter
import com.example.buynow.Adapter.ProductAdapter
import com.example.buynow.Adapter.SaleProductAdapter

import com.example.buynow.Model.Product

import com.example.buynow.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.nio.channels.AsynchronousFileChannel.open


class HomeFragment : Fragment() {

    lateinit var coverRecView:RecyclerView
    lateinit var newRecView:RecyclerView
    lateinit var saleRecView:RecyclerView
    lateinit var coverProduct:ArrayList<Product>
    lateinit var newProduct:ArrayList<Product>
    lateinit var saleProduct:ArrayList<Product>

    lateinit var coverProductAdapter: CoverProductAdapter
    lateinit var newProductAdapter: ProductAdapter
    lateinit var saleProductAdapter: SaleProductAdapter

    lateinit var animationView: LottieAnimationView

    lateinit var newLayout:LinearLayout
    lateinit var saleLayout:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)



        coverProduct = arrayListOf()
        newProduct = arrayListOf()
        saleProduct = arrayListOf()



        coverRecView = view.findViewById(R.id.coverRecView)
        newRecView = view.findViewById(R.id.newRecView)
        saleRecView = view.findViewById(R.id.saleRecView)
        newLayout = view.findViewById(R.id.newLayout)
        saleLayout = view.findViewById(R.id.saleLayout)
        animationView = view.findViewById(R.id.animationView)

        hideLayout()

        setCoverData()


        coverRecView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        coverRecView.setHasFixedSize(true)
        coverProductAdapter = CoverProductAdapter(activity as Context, coverProduct )
        coverRecView.adapter = coverProductAdapter



        newRecView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        newRecView.setHasFixedSize(true)
        newProductAdapter = ProductAdapter(newProduct, activity as Context )
        newRecView.adapter = newProductAdapter


        saleRecView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        saleRecView.setHasFixedSize(true)
        saleProductAdapter = SaleProductAdapter(saleProduct, activity as Context )
        saleRecView.adapter = saleProductAdapter

        showLayout()

        return view
    }



    private fun hideLayout(){
        animationView.playAnimation()
        animationView.loop(true)
        coverRecView.visibility = View.GONE
        newLayout.visibility = View.GONE
        saleLayout.visibility = View.GONE
        animationView.visibility = View.VISIBLE
    }
    private fun showLayout(){
        animationView.pauseAnimation()
        animationView.visibility = View.GONE
        coverRecView.visibility = View.VISIBLE
        newLayout.visibility = View.VISIBLE
        saleLayout.visibility = View.VISIBLE
    }

    fun getJsonData(context: Context, fileName: String): String? {

        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun setCoverData() {

        val jsonFileString = context?.let {

            getJsonData(it, "CoverProducts.json")
        }
        val gson = Gson()

        val listCoverType = object : TypeToken<List<Product>>() {}.type

        var coverD: List<Product> = gson.fromJson(jsonFileString, listCoverType)

        coverD.forEachIndexed { idx, person ->

            coverProduct.add(person)
            newProduct.add(person)
            saleProduct.add(person)

        }


    }



}


