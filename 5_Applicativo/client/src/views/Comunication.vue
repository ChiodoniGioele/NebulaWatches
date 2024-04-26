<template>
    <div class="flex h-screen">
        <Sidebar />
        <div class="flex flex-col w-full items-center justify-center">
            <Card class="w-[350px]">
                <CardHeader>
                    <CardTitle>Work in progress!</CardTitle>
                    <br>
                    <CardDescription>Unfortunately this feature isn't ready yet! Stay tuned for more.</CardDescription>
                </CardHeader>

            </Card>
        </div>
    </div>
</template>

<script setup>
import Sidebar from '@/components/Sidebar.vue'
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from '@/components/ui/card'
</script>