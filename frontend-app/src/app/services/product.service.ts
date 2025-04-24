import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../models/product.model';
import { Observable } from 'rxjs';
import { NGXLogger } from 'ngx-logger';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient, private logger: NGXLogger) {}

  getAll(): Observable<Product[]> {
    this.logger.debug('GET todos los productos');
    return this.http.get<Product[]>(this.apiUrl);
  }

  create(product: Omit<Product, 'id'>): Observable<Product> {
    this.logger.debug('POST nuevo producto', product);
    return this.http.post<Product>(this.apiUrl, product);
  }

  update(id: string, product: Omit<Product, 'id'>): Observable<Product> {
    this.logger.debug(`PUT actualizar producto con id ${id}`, product);
    return this.http.put<Product>(`${this.apiUrl}/${id}`, product);
  }

  delete(id: string): Observable<void> {
    this.logger.debug(`DELETE producto con id ${id}`);
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}