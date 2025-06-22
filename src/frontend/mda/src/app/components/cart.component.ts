import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cart: any = null;
  error = '';

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.api.getCartOrCreate().subscribe({
      next: data => this.cart = data,
      error: err => this.error = err.error?.error || err.message
    });
  }

  checkout(): void {
    this.api.createOrder().subscribe({
      next: res => window.location.href = '/order/' + res.orderId,
      error: err => alert(err.error?.error || err.message)
    });
  }
}
