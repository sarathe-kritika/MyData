import { NgModule, ModuleWithProviders }  from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule }  from '@angular/common';
import { ThmCardComponent } from './thm-card';
import { ThmHeaderComponent } from './thm-header';
import { PanelMenuModule, MenuItem } from 'primeng/primeng';
import { ThmDatePickerComponent } from './thm-date-picker/thm-date-picker.component';

const THM_COMPONENTS = [
  ThmCardComponent,
  ThmHeaderComponent,
  ThmDatePickerComponent
];

@NgModule({
  declarations: [
    THM_COMPONENTS
  ],
  imports: [
    CommonModule
    //  PanelMenuModule
  ],
  exports: [
    THM_COMPONENTS
  ]
})
export class ThmModule {

}
