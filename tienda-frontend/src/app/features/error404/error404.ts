import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TranslatePipe } from '../../shared/translate.pipe';

@Component({
  selector: 'app-error404',
  standalone: true,
  imports: [CommonModule, RouterModule, TranslatePipe],
  templateUrl: './error404.html',
  styleUrls: ['./error404.css']
})
export class Error404Component {}
