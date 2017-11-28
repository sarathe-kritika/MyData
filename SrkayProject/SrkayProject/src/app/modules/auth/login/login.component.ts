import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  public user = { 'email': '', 'password': '' };

  constructor(private router: Router) { }

  login() {
    if (this.user.email === 'w') {
      this.router.navigate(['web/home']);
    }
    if (this.user.email === 's') {
      this.router.navigate(['solitaire/dashboard']);
    }
  }

}
