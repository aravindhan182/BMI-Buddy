package di

import org.koin.dsl.module
import ui.BmiCalculateViewModel

fun appModule(enableNetworkLogs: Boolean) = module {
    factory { BmiCalculateViewModel() }
}