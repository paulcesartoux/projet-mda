import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap, switchMap, catchError, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private baseUrl = 'http://localhost:8080';
  private cartId: number | null = null;

  constructor(private http: HttpClient) { }

  /** Vérifie qu’on est en environnement navigateur */
  private _isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
  }

  createCart(): Observable<{ cartId: number; message: string }> {
    return this.http
      .post<{ cartId: number; message: string }>(`${this.baseUrl}/api/cart/create`, {})
      .pipe(
        tap(res => {
          this.cartId = res.cartId;
          if (this._isBrowser()) {
            window.localStorage.setItem('cartId', res.cartId.toString());
          }
        })
      );
  }

  getProducts(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/api/products`);
  }

  addToCart(productId: number, quantity: number): Observable<any> {
    // Charger cartId du localStorage si besoin (navigateur seulement)
    if (this._isBrowser() && this.cartId === null) {
      const stored = window.localStorage.getItem('cartId');
      this.cartId = stored ? +stored : null;
    }
    // Assurer la création/chargement du panier avant d’ajouter
    return this.getCartOrCreate().pipe(
      switchMap(() =>
        this.http.post<any>(`${this.baseUrl}/api/cart/add`, {
          cartId: this.cartId,
          productId,
          quantity
        })
      )
    );
  }

  getCart(): Observable<any> {
    if (this.cartId === null) {
      throw new Error('Cart not initialized');
    }
    return this.http.get<any>(`${this.baseUrl}/api/cart/${this.cartId}`);
  }

  createOrder(): Observable<{ orderId: number; message: string }> {
    if (this.cartId === null) {
      throw new Error('Cart not initialized');
    }
    return this.http.post<{ orderId: number; message: string }>(
      `${this.baseUrl}/api/cart/${this.cartId}/order`, {}
    );
  }

  payOrder(orderId: number, paymentData: any): Observable<any> {
    return this.http.post<any>(
      `${this.baseUrl}/api/order/${orderId}/pay`, paymentData
    );
  }

  getOrder(orderId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/api/order/${orderId}`);
  }

  /** Récupère le panier existant ou en crée un nouveau */
  getCartOrCreate(): Observable<any> {
    // Charger cartId si possible (SSR safe)
    if (this._isBrowser() && this.cartId === null) {
      const stored = window.localStorage.getItem('cartId');
      this.cartId = stored ? +stored : null;
    }
    // Tenter de récupérer le panier existant, sinon créer un nouveau et recharger
    const load$ = this.cartId !== null
      ? this.getCart()
      : this.createCart().pipe(switchMap(() => this.getCart()));
    return load$.pipe(
      catchError(() =>
        this.createCart().pipe(
          switchMap(() => this.getCart())
        )
      )
    );
  }
}
