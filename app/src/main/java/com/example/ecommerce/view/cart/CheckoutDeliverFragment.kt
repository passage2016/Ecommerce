package com.example.ecommerce.view.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ecommerce.R
import com.example.ecommerce.databinding.DialogAddAddressBinding
import com.example.ecommerce.model.remote.AddressVolleyHandler
import com.example.ecommerce.model.remote.data.address.AddressResponse
import com.example.ecommerce.model.remote.data.address.Addresse
import com.example.ecommerce.presenter.addAddress.AddAddressMVP
import com.example.ecommerce.presenter.addAddress.AddAddressPresenter
import com.example.ecommerce.presenter.getAddress.GetAddressMVP
import com.example.ecommerce.presenter.getAddress.GetAddressPresenter
import com.example.ecommerce.view.LoginActivity
import com.example.ecommerce.view.LoginActivity.Companion.ACCOUNT_INFO_FILE_NAME
import com.example.ecommerce.view.LoginActivity.Companion.USER_ID

class CheckoutDeliverFragment : Fragment(), GetAddressMVP.GetAddressView, AddAddressMVP.AddAddressView {

    private lateinit var getAddressPresenter: GetAddressPresenter
    private lateinit var addAddressPresenter: AddAddressPresenter
    lateinit var adapter: ChooseAddresseAdapter
    lateinit var addressList: ArrayList<Addresse>
    lateinit var currentView: View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var title: String
    lateinit var address: String
    lateinit var rgChooseAddress: RadioGroup

    lateinit var btnIdMap: HashMap<Int, Int>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_checkout_deliver, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        getAddressPresenter = GetAddressPresenter(AddressVolleyHandler(view.context), this)
        addAddressPresenter = AddAddressPresenter(AddressVolleyHandler(view.context), this)
        sharedPreferences = this.requireActivity().getSharedPreferences(
            ACCOUNT_INFO_FILE_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        btnIdMap = HashMap()
        val userId = sharedPreferences.getString(USER_ID, "-1")

        userId?.let {
            getAddressPresenter.getAddress(userId)
        }

        rgChooseAddress = currentView.findViewById<RadioGroup>(R.id.rg_choose_address);

        rgChooseAddress.setOnCheckedChangeListener{
                group: RadioGroup, checkId: Int ->
            val checkRadioButton = group.findViewById<RadioButton>(group.checkedRadioButtonId)

            checkRadioButton?.let {
                val index = btnIdMap.get(checkRadioButton.id)
                if(index != null){
                    title = addressList.get(index).title
                    address = addressList.get(index).address
                }
            }
        }

        val btnCheckoutDeliveryNext: Button = currentView.findViewById(R.id.btn_checkout_delivery_next)
        btnCheckoutDeliveryNext.setOnClickListener {
            editor.putString(ADDRESS_TITLE, title)
            editor.putString(ADDRESS_ADDRESS, address)
            editor.apply()
            (this.parentFragment as CheckoutFragment).nextPager()
        }

        val btnCheckoutDeliveryAddAddress: Button = currentView.findViewById(R.id.btn_checkout_delivery_add_address)
        btnCheckoutDeliveryAddAddress.setOnClickListener {
            showAddBookDialog()

        }
    }

    private fun showAddBookDialog() {
        val dialogBinding = DialogAddAddressBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(currentView.context).apply {
            setView(dialogBinding.root)
            setCancelable(false)
        }

        val dialog = builder.create()
        dialogBinding.apply {
            btnAddAddressSave.setOnClickListener {
                val title = etAddAddressTitle.text.toString()
                val address = etAddAddressAddress.text.toString()
                val userId = sharedPreferences.getString(USER_ID, "-1")
                userId?.let {
                    addAddressPresenter.addAddress(userId, title, address)
                }
                dialog.dismiss()
            }

            btnAddAddressCancel.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }


    override fun setResult(addressResponse: AddressResponse?) {
        addressResponse?.let {

            addressList = addressResponse.addresses
            if (addressList.size > 0) {
                title = addressList.get(0).title
                address = addressList.get(0).address
            } else {
                title = ""
                address = ""
            }

            rgChooseAddress.removeAllViews()



            for (i in 0 until addressList.size) {
                val radioButton: RadioButton = RadioButton(currentView.context);
                val lp: RadioGroup.LayoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )
                lp.setMargins(10, 10, 10, 10)
                radioButton.layoutParams = lp
                radioButton.setBackgroundResource(R.drawable.shape_category_card)
                radioButton.setButtonDrawable(R.drawable.selector_check_circle_outline_24)
                val spannableString: SpannableString = SpannableString(addressList.get(i).title)
                spannableString.setSpan(AbsoluteSizeSpan(20, true), 0, addressList.get(i).title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                radioButton.setText(spannableString)
                radioButton.append("\n")
                radioButton.append(addressList.get(i).address)
                rgChooseAddress.addView(radioButton)
                btnIdMap.put(radioButton.id, i)

                if(i == 0){
                    rgChooseAddress.check(radioButton.id)
                }
            }

//                adapter = ChooseAddresseAdapter(currentView.context, categoryList)
//                currentView.findViewById<RecyclerView>(R.id.rv_choose_address).layoutManager =
//                    LinearLayoutManager(currentView.context)
//                currentView.findViewById<RecyclerView>(R.id.rv_choose_address).adapter = adapter

        }


    }

    override fun setResult(message: String) {
        val userId = sharedPreferences.getString(USER_ID, "-1")
        userId?.let {
            getAddressPresenter.getAddress(userId)
        }
    }

    override fun onLoad(isLoading: Boolean) {
    }

    companion object {
        const val ADDRESS_TITLE = "address_title"
        const val ADDRESS_ADDRESS = "address_address"
    }
}