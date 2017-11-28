import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-web-root',
  templateUrl: './web.component.html',
  styles: [`
    .active{
      color:blue !important;
      font-weight :bold;
    }`]
})
export class WebComponent {

  constructor(private router: Router) { }

}
