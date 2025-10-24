import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authToken = localStorage.getItem("token");

  if (authToken) {
    // Clone the request and add the Authorization header
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${authToken}`
      }
    });
    return next(clonedRequest);
  }

  // If there's no token, just pass the request through
  return next(req);
};
