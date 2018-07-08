package spring.product

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/products")
class ProductPort(val repo: ProductRepo) {

    @PostMapping
    fun post(@RequestBody productView: ProductView): ResponseEntity<ProductView> {

        val product =  Product(
            SKU(productView.sku),
            ProductName(productView.name),
            EAN(productView.ean)
        )

        repo.save(product)

        return ResponseEntity.ok(ProductView.fromProduct(product))
    }


    @GetMapping("/{sku}")
    fun get(@PathVariable sku: String): ResponseEntity<ProductView> {
        val product = repo.findFirstBySku(SKU(sku))

        return if(product != null) {
            ResponseEntity.ok(ProductView.fromProduct(product))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<ProductView>> {
        val list = repo.findAll()
            .map { product -> ProductView.fromProduct(product) }

        return ResponseEntity.ok(list)
    }
}

data class ProductView(
    val sku: String,
    val name: String,
    val ean: String
) {
    companion object {
        fun fromProduct(product: Product): ProductView = ProductView(
            product.sku.value,
            product.name.name,
            product.ean.value
        )
    }
}