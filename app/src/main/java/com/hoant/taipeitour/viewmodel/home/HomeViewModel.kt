package com.hoant.taipeitour.viewmodel.home

import android.app.Application
import com.hoant.taipeitour.repository.repo.HomeRepository
import com.hoant.taipeitour.base.BaseViewModel


class HomeViewModel(app: Application,  private var repository: HomeRepository): BaseViewModel(app) {

}