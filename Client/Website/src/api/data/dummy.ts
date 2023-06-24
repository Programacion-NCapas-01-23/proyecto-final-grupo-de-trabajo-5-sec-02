import Faculty from "@/interfaces/Faculty";
import {CareerPreview} from "@/interfaces/Career";
import ClassTime from "@/interfaces/ClassTime";
import {CyclePreview} from "@/interfaces/Cycle";
import {PensumPreview} from "@/interfaces/Pensum";
import {SubjectPreview} from "@/interfaces/Subject";
import {SchedulePreview} from "@/interfaces/Schedule";

export const faculties: Faculty[] = [
    {
        id: 'b02c7aa5-2c4f-434a-8e63-c6deb7aea4dd',
        name: 'Facultad de Ingenieria y Arquitectura',
        description: 'Facultad de Ingenieria y Arquitectura',
        logo: 'https://premiosegundomontes.uca.edu.sv/wp-content/uploads/2021/12/Logo-negro-con-transparencia-303x400.png',
    },
    {
        id: '7b1cd947-42d2-4e16-84bc-269e8db66842',
        name: 'test',
        description: 'test',
        logo: '',
    },
];

export const careers: CareerPreview[] = [
    {
        id: '5b85d947-d840-4d4a-bb7d-c49a3638f237',
        name: 'Arquitectura',
        facultyId: 'b02c7aa5-2c4f-434a-8e63-c6deb7aea4dd',
    },
    {
        id: 'af10faa7-5e0b-4df0-98fd-855f36fa62e0',
        name: 'Ingeniería de Alimentos',
        facultyId: 'b02c7aa5-2c4f-434a-8e63-c6deb7aea4dd',
    },
];

export const pensums: PensumPreview[] = [
    {
        id: '9871bdee-3c27-4d84-89a4-eeffcd2973c6',
        plan: '2018',
        careerId: '5b85d947-d840-4d4a-bb7d-c49a3638f237',
    },
    {
        id: 'c9cfe06b-36e7-47b5-b54a-bb3349dc3a2e',
        plan: '2019',
        careerId: 'af10faa7-5e0b-4df0-98fd-855f36fa62e0',
    },
];

export const cycles: CyclePreview[] = [
    {
        id: '86079fb4-e912-4466-962b-ea8fb7f53af0',
        name: 'Ciclo I',
        cycleType: 0,
        pensumId: '9871bdee-3c27-4d84-89a4-eeffcd2973c6',
    },
    {
        id: 'c9a7af1e-5fdf-4d25-bf46-286b42cc4e52',
        name: 'Ciclo II',
        cycleType: 1,
        pensumId: '9871bdee-3c27-4d84-89a4-eeffcd2973c6',
    },
    {
        id: '6781851f-e2cf-4ea9-86dd-5274cb82a84c',
        name: 'Ciclo III',
        cycleType: 0,
        pensumId: '9871bdee-3c27-4d84-89a4-eeffcd2973c6',
    },
    {
        id: '3dbd5df7-17e3-4052-a1bd-26c407d061d2',
        name: 'Ciclo IV',
        cycleType: 1,
        pensumId: '9871bdee-3c27-4d84-89a4-eeffcd2973c6',
    },
    {
        id: '49919360-94b5-4adc-87ca-8281fccf268c',
        name: 'Ciclo V',
        cycleType: 0,
        pensumId: '9871bdee-3c27-4d84-89a4-eeffcd2973c6',
    },
];

export const subjects: SubjectPreview[] = [
    {
        name: 'Precalculo',
        code: '010180',
        uv: 4,
    },
    {
        name: 'Taller Espacial I',
        code: '220032',
        uv: 4,
    },
    {
        name: 'Comunicaciones I',
        code: '220066',
        uv: 4,
    },
];

export const classTimes: ClassTime[] = [
    {
        id: '30f115f6-5e7b-493d-8074-9fcc4a46a80d',
        days: 0,
        startHour: "07:00",
        totalHours: 2,
    },
    {
        id: 'aa19ca8f-53ae-48e5-91bf-460ac030321b',
        days: 0,
        startHour: "09:00",
        totalHours: 2,
    },
    {
        id: '466be298-8ab8-4702-b396-ab0c587e2461',
        days: 1,
        startHour: "07:00",
        totalHours: 2,
    },
    {
        id: 'cb142082-a7e0-4934-a86d-8b59d6fcbc75',
        days: 1,
        startHour: "09:00",
        totalHours: 2,
    },
    {
        id: '5186a8f7-e2a0-40b7-8f6e-8c22f8efe9d4',
        days: 2,
        startHour: "07:00",
        totalHours: 2,
    },
    {
        id: '25980cfc-789f-4d3a-afcf-7a61a723260a',
        days: 2,
        startHour: "09:00",
        totalHours: 2,
    },
];

export const schedules: SchedulePreview[] = [
    {
        id: '04100405-ba5c-474f-8a72-35bd4a259c51',
        collection: 0,
        subjectId: '001',
        classTimeId: '30f115f6-5e7b-493d-8074-9fcc4a46a80d',
    },
    {
        id: 'ca5c9302-9980-4c16-a5ba-4fbfdf930c5f',
        collection: 0,
        subjectId: '001',
        classTimeId: '5186a8f7-e2a0-40b7-8f6e-8c22f8efe9d4',
    },
    {
        id: '3a18529c-491b-4661-909c-68a993b431ff',
        collection: 0,
        subjectId: '001',
        classTimeId: 'dcafc869-1780-451a-9a27-a1d01e0fd925',
    },
]



