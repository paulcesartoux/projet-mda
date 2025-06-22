import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: any[] = [];
  error = '';

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.api.getProducts().subscribe({
      next: data => this.products = data,
      error: err => this.error = err.error?.error || err.message
    });
  }

  addToCart(productId: number): void {
    this.api.addToCart(productId, 1).subscribe({
      next: () => alert('Produit ajouté au panier'),
      error: err => alert(err.error?.error || err.message)
    });
  }
}
