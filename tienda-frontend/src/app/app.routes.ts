import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home';
import { LoginComponent } from './features/login/login';
import { AuthGuard } from './core/guards/auth-guard';
import { AdminGuard } from './core/guards/admin-guard';
import { RegisterComponent } from './features/register/register';
import { Error404Component } from './features/error404/error404';
import { ForbiddenComponent } from './features/forbidden/forbidden';
import { FacturasComponent } from './features/facturas/facturas';
import { ProductosComponent } from './features/productos/productos';
import { GithubCallbackComponent } from './features/login/github-callback.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'oauth2/callback/github', component: GithubCallbackComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forbidden', component: ForbiddenComponent },
  { path: 'productos', component: ProductosComponent },
  { path: 'productos/:categoria', component: ProductosComponent },
  { path: 'productos/tipo/:tipo', component: ProductosComponent },
  { path: 'producto/:id', loadComponent: () => import('./features/producto-detalle/producto-detalle').then(m => m.ProductoDetalleComponent) },
  { path: 'cart', loadComponent: () => import('./features/cart/cart').then(m => m.CartComponent) },
  { path: 'facturas', component: FacturasComponent, canActivate: [AuthGuard] },
  { path: 'admin/productos', loadComponent: () => import('./features/admin-productos/admin-productos').then(m => m.AdminProductosComponent), canActivate: [AdminGuard] },
  { path: '404', component: Error404Component },
  { path: '**', redirectTo: '/404' }
];

