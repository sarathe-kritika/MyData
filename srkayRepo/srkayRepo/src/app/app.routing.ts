import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './modules/auth/login/login.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'web', loadChildren: 'app/modules/web/web.module#WebModule' },
  { path: 'solitaire', loadChildren: 'app/modules/solitaire/solitaire.module#SolitaireModule' },
  { path: '**', redirectTo: 'login' }
];

export const routing = RouterModule.forRoot(routes);
