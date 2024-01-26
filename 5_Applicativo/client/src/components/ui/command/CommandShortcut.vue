<script setup lang="ts">
import { cn } from '@/lib/utils'
</script>

<template>
  <span :class="cn('ml-auto text-xs tracking-widest text-slate-500 dark:text-slate-400', $attrs.class ?? '')">
    <slot />
  </span>
</template>
