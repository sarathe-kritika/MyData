import { NgModule } from '@angular/core';
import { CommonModule }  from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { webRouting } from './web.routing';
import { WebComponent } from './web.component';
import { SearchComponent } from './search/search.component';
import { BasketComponent } from './basket/basket.component';
import { ViewRequestComponent } from './view-request/view-request.component';
import { BidListComponent } from './bid-list/bid-list.component';
import { SavedSearchComponent } from './search/saved-search/saved-search.component';
import { HomeComponent } from './home/home.component';
import { ApiService }  from '../../common/services/api.service';
import { ThmModule } from '../../theme/thm.module';
import { TranslateModule } from 'ng2-translate';

@NgModule({
  declarations: [
    WebComponent,
    SearchComponent,
    BasketComponent,
    ViewRequestComponent,
    BidListComponent,
    SavedSearchComponent,
    HomeComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    webRouting,
    TranslateModule,
    ThmModule
  ],
  providers: [ApiService]
})
export class WebModule { }
