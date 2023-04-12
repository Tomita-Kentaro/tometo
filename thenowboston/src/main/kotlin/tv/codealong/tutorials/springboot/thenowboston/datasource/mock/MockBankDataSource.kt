package tv.codealong.tutorials.springboot.thenowboston.datasource.mock

import org.springframework.stereotype.Repository
import tv.codealong.tutorials.springboot.thenowboston.datasource.BankDataSource
import tv.codealong.tutorials.springboot.thenowboston.model.Bank

@Repository
class MockBankDataSource : BankDataSource {

    val banks = listOf(Bank("", 0.0, 1))

    override fun getBanks(): Collection<Bank> = banks
}