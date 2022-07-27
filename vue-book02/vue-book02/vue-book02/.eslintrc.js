module.exports = {
    "env": {
        "browser": true,
        "es6": true,
        "commonjs": true,
        "node": true,
    },
    "extends": [
        "eslint:recommended",
        "plugin:vue/vue3-essential"
    ],
    "parserOptions": {
        "ecmaVersion": "latest",
        "sourceType": "module"
    },
    "plugins": [
        "vue"
    ],
    "rules": {
        'space-before-function-paren': 0, // 函数后面不加空格
        'vue/multi-word-component-names': 0 // 第一行报错
    }
}
