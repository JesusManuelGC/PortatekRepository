import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TranslatePipe } from '../../shared/translate.pipe';

@Component({
  selector: 'app-forbidden',
  standalone: true,
  imports: [CommonModule, RouterModule, TranslatePipe],
  templateUrl: './forbidden.html',
  styleUrls: ['./forbidden.css']
})
export class ForbiddenComponent {}
