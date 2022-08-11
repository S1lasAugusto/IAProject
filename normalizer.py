import json

def normalizeAttribute(attr, dataset):
    maxAttrValue = 0

    for pokemon in dataset['pokemons']:
        attrValue = pokemon['level_data'][len(pokemon['level_data']) - 1][attr]
        maxAttrValue = attrValue if attrValue > maxAttrValue else maxAttrValue
        
    for pokemon in dataset['pokemons']:
        for level_data in pokemon['level_data']:
            level_data[attr] = level_data[attr] / maxAttrValue

    for heldItem in dataset['held_items']:
        if (attr not in heldItem):
            print(attr)
            break
        heldItem[attr] = heldItem[attr] / maxAttrValue

def normalizePercentageAttr(attr, dataset):
    maxAttrValue = 100
    
    for pokemon in dataset['pokemons']:
        for level_data in pokemon['level_data']:
            level_data[attr] = level_data[attr] / maxAttrValue

    for heldItem in dataset['held_items']:
        if (attr not in heldItem):
            break
        heldItem[attr] = heldItem[attr] / maxAttrValue

file = open('./input/Dataset.json')

dataset = json.load(file)

attributesToNormalize = ['hp', 'heal_5s', 'attack', 'defense', 'sp_attack', 'sp_defense', 'move_speed', 'attack_speed']
percentageAttributesToNormalize = ['cdr', 'life_steal', 'tenacity']

# normalizing
for attr in attributesToNormalize:
    normalizeAttribute(attr, dataset)

for attr in percentageAttributesToNormalize:
    normalizePercentageAttr(attr, dataset)

with open('./input/NormalizedDataset.json', 'w') as outputFile:
    json.dump(dataset, outputFile)