package com.hoant.taipeitour.view.home

import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import com.hoant.taipeitour.R
import com.hoant.taipeitour.base.BaseActivity
import com.hoant.taipeitour.databinding.ActivityMainBinding
import com.hoant.taipeitour.repository.api.ApiClient
import com.hoant.taipeitour.repository.repo.HomeRepository
import com.hoant.taipeitour.viewmodel.home.HomeViewModel

class HomeActivity : BaseActivity<HomeViewModel, ActivityMainBinding, HomeRepository>() {

    override var variableId: Int? = null

    override fun getViewBinding(): ActivityMainBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    override fun getRepository(): HomeRepository {
        return HomeRepository(ApiClient.homeApi)
    }

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)

    }
}