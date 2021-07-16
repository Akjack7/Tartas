package com.example.cakeslist.domain.usecases

import com.example.cakeslist.data.CakeRepository
import com.example.cakeslist.data.models.Cake
import kotlinx.coroutines.flow.Flow

typealias GetCakesUseBaseCase = BaseUseCase<Unit, Flow<List<Cake>>>

class GetCakesUseCase(
    private val repository: CakeRepository
) : GetCakesUseBaseCase {

    override suspend fun invoke(params: Unit) = repository.getCakes()

}