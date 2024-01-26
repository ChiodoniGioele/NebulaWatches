<script setup lang="ts">
import {
  MenubarCheckboxItem,
  type MenubarCheckboxItemEmits,
  type MenubarCheckboxItemProps,
  MenubarItemIndicator,
} from 'radix-vue'
import { Check } from 'lucide-vue-next'
import { cn } from '@/lib/utils'

const props = defineProps<MenubarCheckboxItemProps & { class?: string }>()

const emit = defineEmits<MenubarCheckboxItemEmits>()
</script>

<template>
  <MenubarCheckboxItem
    v-bind="props"
    :class="[
      cn(
        'relative flex cursor-default select-none items-center rounded-sm py-1.5 pl-8 pr-2 text-sm outline-none focus:bg-slate-100 focus:text-slate-900 data-[disabled]:pointer-events-none data-[disabled]:opacity-50 dark:focus:bg-slate-800 dark:focus:text-slate-50',
        props.class,
      ),
    ]"
    @update:checked="emit('update:checked', $event)"
    @select="emit('select', $event)"
  >
    <MenubarItemIndicator
      class="absolute left-2 flex h-3.5 w-3.5 items-center justify-center"
    >
      <Check class="w-4 h-4" />
    </MenubarItemIndicator>
    <slot />
  </MenubarCheckboxItem>
</template>
