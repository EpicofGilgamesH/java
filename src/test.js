function IacesarSingal() {
    class Pizza {
        constructor(yours) {
            this.yours = yours;
            this.sequence = [1, 2, 3, 4, 5, 6, 7, 8, 9].map(n => Math.pow(2, n))
                .sort((a, b) => a > b ? -1 : (a < b) ? 1 : 0);
            this.cipher = [];
        }

        getCipher() {
            this.sequence.reduce((total, piece) => {
                if (total + piece > this.yours) return total;
                this.cipher.push(piece);
                return total += piece;
            }, 0);
            this.cipher.sort((a, b) => a > b ? 1 : (a < b) ? -1 : 0);
            return this;
        }

        decrypt(dictionary) {
            return this.cipher.map((atom, idx) => dictionary[atom + this.keys[idx]]).join("");
        }

        getkeys() {
            this.keys = [5, 1, 1, -92, -490];
            return this;
        }
    }

    let dictionary = ["自", "由", "自", "在", "功", "不", "可", "没", "卓", "有", "成", "效", "大", "吉", "大", "利", "A", "B", "C"
        , "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",]
    return new Pizza(666).getkeys().getCipher().decrypt(dictionary);
}

console.log(IacesarSingal())


