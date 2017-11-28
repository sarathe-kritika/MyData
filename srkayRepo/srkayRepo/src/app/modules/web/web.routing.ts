import { Routes, RouterModule } from '@angular/router';
import { WebComponent } from './web.component';
import { HomeComponent } from './home/home.component';
import { SearchComponent } from './search/search.component';
import { BasketComponent } from './basket/basket.component';
import { ViewRequestComponent } from './view-request/view-request.component';
import { BidListComponent } from './bid-list/bid-list.component';
import { SavedSearchComponent } from './search/saved-search/saved-search.component';

const routes: Routes = [
  {
    path: '', component: WebComponent, children: [
      { path: 'home', component: HomeComponent, pathMatch: 'full' },
      { path: 'search', component: SearchComponent, pathMatch: 'full' },
      { path: 'basket', component: BasketComponent, pathMatch: 'full' },
      { path: 'view-request', component: ViewRequestComponent, pathMatch: 'full' },
      { path: 'bid-list', component: BidListComponent, pathMatch: 'full' },
      { path: 'saved-search', component: SavedSearchComponent, pathMatch: 'full' },
    ]
  }
];

export const webRouting = RouterModule.forChild(routes);
