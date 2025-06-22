import { Routes } from '@angular/router';
import { ProductsComponent } from './components/products.component';
import { CartComponent } from './components/cart.component';
import { OrderComponent } from './components/order.component';

export const routes: Routes = [
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  { path: 'products', component: ProductsComponent },
  { path: 'cart', component: CartComponent },
  { path: 'order/:orderId', component: OrderComponent }
];
