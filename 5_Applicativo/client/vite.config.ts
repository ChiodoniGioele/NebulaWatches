import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import fs from 'fs';
import vue from '@vitejs/plugin-vue'
import tailwind from "tailwindcss"
import autoprefixer from "autoprefixer"


// https://vitejs.dev/config/
export default defineConfig({
  server: {
    https: {
      key: fs.readFileSync('./server.key'),
      cert: fs.readFileSync('./server.cert'),
    },
    port: 3000, // Adjust the port number as needed
  },

  plugins: [
    vue(),
  ],
  css: {
    postcss: {
      plugins: [tailwind(), autoprefixer()],
    },
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
