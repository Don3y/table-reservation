export interface MyReservation {
    iid: number;
    uid: number;
    type: 'Vip' | 'Smoking' | 'Outsider';
    details: string;
    timestamp: number;
}