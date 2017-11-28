import { NgModule } from '@angular/core';
import { CommonModule }  from '@angular/common';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';
import { SolitaireRouting, routes } from './solitaire.routing';
import { SolitaireComponent } from './solitaire.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ThmModule } from '../../theme/thm.module';
import {MenubarModule, MenuItem} from 'primeng/primeng';
import { ProfileComponent } from './profile/profile.component';
import {TranslateModule} from 'ng2-translate';
import { TableComponent } from './table/table.component';
import {CarService}  from "../../common/services/car.service";
import {DataTableModule, SharedModule, MultiSelectModule} from 'primeng/primeng';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    JsonpModule,
    TranslateModule,
    SolitaireRouting,
    ThmModule,
    MenubarModule,
    DataTableModule,
    SharedModule,
    MultiSelectModule
  ],
  declarations: [
    SolitaireComponent,
    DashboardComponent,
    ProfileComponent,
    TableComponent
  ],
  providers: [CarService]
})
export class SolitaireModule {

}
