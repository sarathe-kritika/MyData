import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SolitaireComponent } from './solitaire.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProfileComponent } from "./profile/profile.component";
import {TableComponent} from "./table/table.component";
export const routes: Routes = [
  {
    path: '', component: SolitaireComponent, children: [
      { path: 'dashboard', component: DashboardComponent, pathMatch: 'full' },
      { path: 'profile', component: ProfileComponent, pathMatch: 'full' },
      { path: 'table', component: TableComponent, pathMatch: 'full' }
    ]
  }
];

export const SolitaireRouting = RouterModule.forChild(routes);
