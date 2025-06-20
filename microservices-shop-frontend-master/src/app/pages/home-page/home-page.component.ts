import {Component, inject, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {Product} from "../../model/product";
import {ProductService} from "../../services/product/product.service";
import {AsyncPipe, JsonPipe} from "@angular/common";
import {Router} from "@angular/router";
import {Order} from "../../model/order";
import {FormsModule} from "@angular/forms";
import {OrderService} from "../../services/order/order.service";

@Component({
  selector: 'app-homepage',
  templateUrl: './home-page.component.html',
  standalone: true,
  imports: [
    AsyncPipe,
    JsonPipe,
    FormsModule
  ],
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit {
  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly productService = inject(ProductService);
  private readonly orderService = inject(OrderService);
  private readonly router = inject(Router);
  isAuthenticated = false;
  products: Array<Product> = [];
  quantityIsNull = false;
  orderSuccess = false;
  orderFailed = false;

  searchQuery: string = '';
  isSearching = false;

  selectedCategory: string | null = null;

  currentSlide = 0;
  slides = [
    { image: '/slider-main/jlow.png', alt: 'Акция 1' },
    { image: '/slider-main/jordan.png', alt: 'Акция 2' },
    { image: '/slider-main/vultureshoodie.png', alt: 'Акция 3' }
  ];

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({isAuthenticated}) => {
        this.isAuthenticated = isAuthenticated;
        this.productService.getProducts()
          .pipe()
          .subscribe(product => {
            this.products = product;
          })
      }
    );
    
    setInterval(() => this.nextSlide(), 5000);
  }

  onSearch(): void {
    if (this.searchQuery.trim()) {
      this.isSearching = true; 
      this.productService.searchProducts(this.searchQuery.trim())
        .subscribe({
          next: (products) => {
            this.products = products;
            this.orderSuccess = false;
            this.orderFailed = false;
          },
          error: (err) => {
            console.error('Ошибка поиска:', err);
            this.products = [];
          }
        });
    } else {
      this.isSearching = false; 
    }
  }

  filterByCategory(category: string): void {
    this.selectedCategory = category;
    this.isSearching = true;
    this.productService.findProductsByCategory(category)
      .subscribe({
        next: (products) => {
          this.products = products;
          this.orderSuccess = false;
          this.orderFailed = false;
        },
        error: (err) => {
          console.error('Ошибка фильтрации:', err);
          this.products = [];
        }
      });
  }

  clearFilters(): void {
    this.selectedCategory = null;
    this.isSearching = false;
    this.searchQuery = '';
    this.productService.getProducts()
      .subscribe(products => {
        this.products = products;
      });
  }

  getCategoryName(categoryKey: string): string {
    const categoryNames: {[key: string]: string} = {
      'clothing': 'Одежда',
      'shoes': 'Обувь',
      'accessories': 'Аксессуары'
    };
    return categoryNames[categoryKey] || categoryKey;
  }

  nextSlide(): void {
    this.currentSlide = (this.currentSlide + 1) % this.slides.length;
  }

  prevSlide(): void {
    this.currentSlide = (this.currentSlide - 1 + this.slides.length) % this.slides.length;
  }

  goToSlide(index: number): void {
    this.currentSlide = index;
  }

  goToCreateProductPage() {
    this.router.navigateByUrl('/add-product');
  }

  orderProduct(product: Product, quantity: string) {

    this.oidcSecurityService.userData$.subscribe(result => {
      const userDetails = {
        email: result.userData.email,
        firstName: result.userData.firstName,
        lastName: result.userData.lastName
      };

      if(!quantity) {
        this.orderFailed = true;
        this.orderSuccess = false;
        this.quantityIsNull = true;
      } else {
        const order: Order = {
          skuCode: product.skuCode,
          price: product.price,
          quantity: Number(quantity),
          userDetails: userDetails
        }

        this.orderService.orderProduct(order).subscribe({
          next: () => {
            this.orderSuccess = true;
            this.orderFailed = false;
          },
          error: (error) => {
            this.orderFailed = true;
            this.orderSuccess = false;
            console.error('Ошибка при заказе:', error);
          }
        });
      }
    })
  }
}
