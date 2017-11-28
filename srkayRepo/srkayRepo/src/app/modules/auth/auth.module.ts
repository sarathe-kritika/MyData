import { NgModule } from '@angular/core';
import { CommonModule }  from '@angular/common';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';
import { LoginComponent } from './login/login.component';
import { PanelMenuModule, MenuItem } from 'primeng/primeng';
import { ThmModule } from '../../theme/thm.module';
import { TranslateModule } from 'ng2-translate';
import { AuthRouting } from "./auth.routing";

const PRIME_NG_MODULES = [
  PanelMenuModule
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    JsonpModule,
    PRIME_NG_MODULES,
    ThmModule,
    TranslateModule,
    AuthRouting
  ],
  declarations: [
    LoginComponent
  ]
})
export class AuthModule {
}
