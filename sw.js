const CACHE = 'miswak-v1';
const FILES = [
  '/',
  '/index.html',
  '/css/styles.css',
  '/js/main.js',
  '/js/state.js',
  '/js/data.js',
  '/js/actions.js',
  '/js/auth.js',
  '/js/helpers.js',
  '/js/render-customer.js',
  '/js/render-admin.js',
  '/js/realtime.js',
  '/js/legal-invoice.js',
  '/js/supabase-client.js',
];

self.addEventListener('install', e => {
  e.waitUntil(caches.open(CACHE).then(c => c.addAll(FILES)));
  self.skipWaiting();
});

self.addEventListener('activate', e => {
  e.waitUntil(caches.keys().then(keys =>
    Promise.all(keys.filter(k => k !== CACHE).map(k => caches.delete(k)))
  ));
  self.clients.claim();
});

self.addEventListener('fetch', e => {
  e.respondWith(
    fetch(e.request).catch(() => caches.match(e.request))
  );
});
