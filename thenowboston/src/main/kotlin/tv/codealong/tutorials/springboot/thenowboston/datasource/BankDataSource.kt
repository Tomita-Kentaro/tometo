package tv.codealong.tutorials.springboot.thenowboston.datasource

import tv.codealong.tutorials.springboot.thenowboston.model.Bank

interface BankDataSource {

    fun getBanks(): Collection<Bank>
}