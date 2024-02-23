<template>
    <form class="w-2/3 space-y-6 mx-auto" @submit="onSubmit">
      <FormField v-slot="{ componentField }" name="pin">
        <FormItem>
          <FormLabel>Verification</FormLabel>
          <FormControl>
            <PinInput
              id="pin-input"
              placeholder="○"
              class="flex gap-2 items-center mt-1"
              otp
              type="number"
              :name="componentField.name"
              @complete="handleComplete"
              @update:model-value="(arrStr) => {
                setValues({
                  pin: arrStr.filter(Boolean),
                })
              }"
            >
              <PinInputInput
                v-for="(id, index) in 6"
                :key="id"
                :index="index"
              />
            </PinInput>
          </FormControl>
          <FormDescription>
            Insert the verification code we sent you!
          </FormDescription>
          <FormMessage />
        </FormItem>
      </FormField>
  
      <Button>Submit</Button>
    </form>
  </template>
  
  <script setup lang="ts">
  import { h } from 'vue'
  import { useForm } from 'vee-validate'
  import { toTypedSchema } from '@vee-validate/zod'
  import * as z from 'zod'
  
  
  import { Button } from '@/components/ui/button'
  import {
    FormControl,
    FormDescription,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
  } from '../components/ui/form'
  
  const formSchema = toTypedSchema(z.object({
    pin: z.array(z.coerce.string()).length(6, { message: 'Invalid input' }),
  }))
  
  const { handleSubmit, setValues } = useForm({
    validationSchema: formSchema,
    initialValues: {
      pin: [],
    },
  })
  
  const onSubmit = handleSubmit(({ pin }) => {
    
  })
  
  const handleComplete = (e: string[]) => console.log(e.join(''))
  </script>