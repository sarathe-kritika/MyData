import { Component, LOCALE_ID, Inject } from '@angular/core';
import { TranslateService } from 'ng2-translate';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styles: [`
    .active{
      color:blue !important;
      font-weight :bold;
    }`]
})
export class AppComponent {
  param = { value: 'world' };

  constructor(private translate: TranslateService) {
    translate.addLangs(['en', 'es', 'zh']);
    translate.setDefaultLang('en');
    let browserLang = translate.getBrowserLang();
    translate.use('en');
    //translate.use(browserLang.match(/en|es|zh/) ? browserLang : 'en');
  }

  changeLanguage(lang: string) {
    this.translate.use(lang);
  }
}
