import type {Author} from "./Author.ts";

export interface Book {
    isbn: string;
    title: string;
    price: number;
    inventorySold:number;
    inventorySupplied:number;
    authors: Array<Author>;
}
