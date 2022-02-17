package com.example.testrightmove.model

data class ResponseModel(
	val properties: List<PropertiesItem?>? = null
)

data class PropertiesItem(
	val bedrooms: Int? = null,
	val number: String? = null,
	val address: String? = null,
	val price: Int? = null,
	val propertyType: String? = null,
	val postcode: String? = null,
	val id: Int? = null,
	val bathrooms: Int? = null,
	val region: String? = null
)

