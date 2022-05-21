package com.binar.mymovieview.di

import com.binar.mymovieview.data.local.favorite.FavoriteRepository
import com.binar.mymovieview.data.local.userauth.UserRepository
import com.binar.mymovieview.data.remote.model.populer.MoviesRepository
import com.binar.mymovieview.ui.detail.DetailViewModel
import com.binar.mymovieview.ui.favorite.FavoriteViewModel
import com.binar.mymovieview.ui.home.HomeViewModel
import com.binar.mymovieview.ui.login.LoginViewModel
import com.binar.mymovieview.ui.profile.ProfileViewModel
import com.binar.mymovieview.ui.register.RegisterViewModel
import com.binar.mymovieview.ui.splashscreen.SplashViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {

}

@DelicateCoroutinesApi
val viewModelModule = module {
    viewModel { HomeViewModel(get(),get()) }
    viewModel {DetailViewModel(get())}
    viewModel {FavoriteViewModel(get())}
    viewModel {LoginViewModel(repository = get(),get())}
    viewModel {ProfileViewModel(get(),get(),get())}
    viewModel {RegisterViewModel(get())}
    viewModel {SplashViewModel(get())}

}

val repositoryModule= module {
    single { FavoriteRepository(get()) }
    single { UserRepository(get(),androidContext()) }
    single { MoviesRepository(get()) }
}

