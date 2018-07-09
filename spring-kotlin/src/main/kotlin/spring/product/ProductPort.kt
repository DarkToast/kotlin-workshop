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
            ArticleNo(productView.articleNo),
            ProductName(productView.name),
            EAN(productView.ean)
        )

        repo.save(product)

        return ResponseEntity.ok(ProductView.fromProduct(product))
    }


    @GetMapping("/{articleNo}")
    fun get(@PathVariable sku: String): ResponseEntity<ProductView> {
        val product = repo.findFirstByArticleNo(ArticleNo(sku))

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
    val articleNo: String,
    val name: String,
    val ean: String
) {
    companion object {
        fun fromProduct(product: Product): ProductView = ProductView(
            product.articleNo.value,
            product.name.name,
            product.ean.value
        )
    }
}