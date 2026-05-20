import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TranslateService {
  private currentLang = 'es';
  private translations: any = {};
  private translationsSubject = new BehaviorSubject<any>({});

  constructor(private http: HttpClient) {
    const savedLang = localStorage.getItem('language');
    if (savedLang) {
      this.currentLang = savedLang;
    }
    this.loadTranslations();
  }

  loadTranslations(): void {
    this.http.get<any>(`/assets/i18n/${this.currentLang}.json`).subscribe({
      next: (translations) => {
        this.translations = translations;
        this.translationsSubject.next(translations);
      },
      error: () => {
        console.error('Error loading translations');
      }
    });
  }

  setLanguage(lang: string): void {
    this.currentLang = lang;
    localStorage.setItem('language', lang);
    this.loadTranslations();
  }

  getLanguage(): string {
    return this.currentLang;
  }

  get(key: string): string {
    const keys = key.split('.');
    let value = this.translations;
    
    for (const k of keys) {
      if (value && value[k]) {
        value = value[k];
      } else {
        return key;
      }
    }
    
    return value || key;
  }

  getTranslations(): Observable<any> {
    return this.translationsSubject.asObservable();
  }
}
