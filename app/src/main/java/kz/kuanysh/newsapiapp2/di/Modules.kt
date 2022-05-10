package kz.kuanysh.newsapiapp2.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kz.kuanysh.newsapiapp2.data.data_source.local.AppDatabase
import kz.kuanysh.newsapiapp2.data.data_source.remote.NetworkApi
import kz.kuanysh.newsapiapp2.data.repository.ArticleRepositoryImpl
import kz.kuanysh.newsapiapp2.domain.repository.ArticleRepository
import kz.kuanysh.newsapiapp2.domain.usecase.GetArticlesUseCase
import kz.kuanysh.newsapiapp2.domain.usecase.LoadArticlesUseCase
import kz.kuanysh.newsapiapp2.ui.news_list.NewsListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://newsapi.org/"

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, AppDatabase.DB_NAME
        ).build()
    }

    single { get<AppDatabase>().getArticleDao() }
}

val remoteModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    single {
        get<Retrofit>().create(NetworkApi::class.java)
    }
}

val repoModule = module {
    factory<ArticleRepository> {
        ArticleRepositoryImpl(
            remoteSource = get(),
            localSource = get(),
        )
    }
}

val domainModule = module {
    factory {
        GetArticlesUseCase(repository = get())
    }

    factory {
        LoadArticlesUseCase(repository = get())
    }
}

val viewModelModule = module {

    viewModel {
        NewsListViewModel(getArticlesUseCase = get(), loadArticlesUseCase = get())
    }
}