import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  order: any = null;
  error = '';

  constructor(private api: ApiService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('orderId'));
    this.api.getOrder(id).subscribe({
      next: data => this.order = data,
      error: err => this.error = err.error?.error || err.message
    });
  }

  pay(): void {
    if (!this.order) return;
    const paymentData = { method: 'card', cardNumber: '1234567890123456', expiry: '12/25', cvv: '123' };
    this.api.payOrder(this.order.orderId, paymentData).subscribe({
      next: res => this.order = res,
      error: err => this.error = err.error?.error || err.message
    });
  }
}
