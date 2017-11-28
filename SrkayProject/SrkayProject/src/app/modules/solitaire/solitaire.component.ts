import { Component, OnInit } from '@angular/core';
import {MenuItem} from 'primeng/primeng';
import {TranslateService} from 'ng2-translate';

@Component({
  selector: 'app-solitaire',
  templateUrl: './solitaire.component.html',
  styleUrls: ['./solitaire.component.scss']
})
export class SolitaireComponent implements OnInit {

  private items: MenuItem[];

  constructor(private translate: TranslateService) { }

  ngOnInit() {
    this.initMenuItems();
  }

  changeLanguage(lang: string) {
    this.translate.use(lang);
    this.initMenuItems();
  }

  translateString(stringValue): string {
    let value = "";
    this.translate.get(stringValue, { value: '' }).subscribe((res: string) => {
      value = res;
    });
    return value;
  }

  initMenuItems() {
    this.items = [
      {
        label: this.translateString('Dashboard'),
        routerLink: ['solitaire/dashboard']
      },
      {
        label: this.translateString('Profile'),
        routerLink: ['solitaire/profile']
      },
      {
        label: "Table",
        routerLink: ['solitaire/table']
      },
      {
        label: this.translateString('Translate'),
        icon: 'fa-edit',
        items: [
          { label: 'English', command: (event) => { this.changeLanguage('en') } },
          { label: 'Spanish', command: (event) => { this.changeLanguage('es') } },
          { label: 'Chinese', command: (event) => { this.changeLanguage('zh') } }
        ]
      },
      {
        label: 'Logout',
        routerLink: ['/logout']
      }
    ];
  }
}
